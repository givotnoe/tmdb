package io.stoptalking.pet_tmdb.main.dagger

import dagger.Component
import io.stoptalking.pet_tmdb.main.MainActivity
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope

@ViewScope
@Component(dependencies = [ActivityComponent::class], modules = [

    MainModule::class
])

interface MainComponent {

    //injects
    fun inject (fragment : MainActivity)
}