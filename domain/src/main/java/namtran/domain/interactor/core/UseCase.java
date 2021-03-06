/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package namtran.domain.interactor.core;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import namtran.domain.executor.SchedulerProvider;
import namtran.domain.repository.IAppRepository;
import namtran.util.Preconditions;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T, Params> {

  private final CompositeDisposable disposables;
  private SchedulerProvider schedulerProvider;
  protected IAppRepository iAppRepository;

  public UseCase(IAppRepository iAppRepository, SchedulerProvider schedulerProvider) {
    this.disposables = new CompositeDisposable();
    this.schedulerProvider = schedulerProvider;
    this.iAppRepository = iAppRepository;
  }

  /**
   * Builds an {@link Flowable} which will be used when executing the current {@link UseCase}.
   */
  protected abstract Flowable<T> buildUseCaseFlowable(Params params);

  /**
   * Builds an {@link Flowable} which will be used when executing the current {@link UseCase}.
   */
  protected abstract Observable<T> buildUseCaseObserve(Params params);

  /**
   * Executes the current use case.
   *
   * @param subscriber {@link DisposableSubscriber} which will be listening to the observable build
   * by {@link #buildUseCaseFlowable(Params)} ()} method.
   * @param params Parameters (Optional) used to build/execute this use case.
   */
  public void execute(DisposableSubscriber<T> subscriber, Params params) {
    Preconditions.checkNotNull(subscriber);
    if (subscriber.isDisposed())
      subscriber.dispose();
    final Flowable<T> observable = this.buildUseCaseFlowable(params)
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui());
    addDisposable(observable.subscribeWith(subscriber));
  }

  /**
   * Executes the current use case.
   *
   * @param observer {@link DisposableObserver} which will be listening to the observable build
   * by {@link #buildUseCaseObserve(Params)} ()} method.
   * @param params Parameters (Optional) used to build/execute this use case.
   */
  public void execute(DisposableObserver<T> observer, Params params) {
    Preconditions.checkNotNull(observer);
    if (observer.isDisposed())
      observer.dispose();
    final Observable<T> observable = this.buildUseCaseObserve(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui());
    addDisposable(observable.subscribeWith(observer));
  }

  /**
   * Dispose from current {@link CompositeDisposable}.
   */
  public void dispose() {
    if (!disposables.isDisposed()) {
      disposables.dispose();
    }
  }

  /**
   * Dispose from current {@link CompositeDisposable}.
   */
  private void addDisposable(Disposable disposable) {
    Preconditions.checkNotNull(disposable);
    Preconditions.checkNotNull(disposables);
    disposables.add(disposable);
  }
}
