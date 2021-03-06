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
package namtran.cleanarchitechturesample.base;

import android.arch.lifecycle.MutableLiveData;

import io.reactivex.observers.DisposableObserver;
import namtran.cleanarchitechturesample.Resource;

/**
 * Default {@link DisposableObserver} base class to be used whenever you want default error handling.
 */
public abstract class DefaultObserve<T> extends DisposableObserver<T> {

  private MutableLiveData<Resource<T>> results;

  public DefaultObserve(MutableLiveData<Resource<T>> results) {
    this.results = results;
  }

  @Override
  protected void onStart() {
    results.setValue(Resource.<T>loading(null));
  }

  @Override
  public void onNext(T t) {
    results.setValue(Resource.success(t));
  }

  @Override
  public void onError(Throwable exception) {
    results.setValue(Resource.<T>error(exception.getMessage(), null));
  }

  @Override
  public void onComplete() {

  }
}
