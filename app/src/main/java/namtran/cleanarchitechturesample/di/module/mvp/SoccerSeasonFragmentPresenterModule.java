package namtran.cleanarchitechturesample.di.module.mvp;

import dagger.Binds;
import dagger.Module;
import namtran.cleanarchitechturesample.application.mvp.presenter.SoccerSeasonFragmentPresenter;
import namtran.cleanarchitechturesample.application.mvp.presenter.iview.IISoccerSeasonFragment;
import namtran.cleanarchitechturesample.di.inject.PerFragment;

/**
 * Provides example 1 presenter dependencies.
 */
@Module
public abstract class SoccerSeasonFragmentPresenterModule {

    @Binds
    @PerFragment
    abstract IISoccerSeasonFragment.IPresenter example1Presenter(SoccerSeasonFragmentPresenter presenter);
}
