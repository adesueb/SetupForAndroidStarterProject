package com.ruangkajian.android.rule

import android.app.Activity
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import com.ruangkajian.android.RuangKajianApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import org.junit.runner.Description
import org.junit.runners.model.Statement
import javax.inject.Provider

/**
 * Activity test rule that provides common functionality of:
 *  1. Providing hooks to inject activity under test.
 *
 * PS: If you add any more features in this class please add it in above list and put an example
 * below.
 *
 *
 * Example Test:
 *
 *  class MyActivityTest {
 *
 *    @get:Rule
 *    val rule = InjectedActivityTestRule(MyActivity::class.java) { activity ->
 *
 *      // Inject your activity dependencies in this injector block.
 *      activity.Presenter = mockPresenter
 *    }
 *
 *    private lateinit var mockPresenter: MyPresenter
 *
 *    @Before
 *    fun setup() {
 *      // Make sure you initialize all your activity dependencies before calling launchActivity()
 *      mockPresenter = mock()
 *
 *      // Launch activity either in setup or in your test after all dependencies are initialized.
 *      rule.launchActivity()
 *    }
 *  }
 *
 *  You need to pass a lambda block to constructor of this class that will be called when activity
 *  calls Injector.inject(this) in its onCreate() method. You can set mock instances of any
 *  dependencies of the activity in this lambda block.
 *
 */
class InjectedActivityTestRule<T : Activity>(
    private val activityClass: Class<T>,
    private val activityInjector: (T) -> Unit
) : ActivityTestRule<T>(
    activityClass,
    false,
    false
) {

    lateinit var tempDispatching: DispatchingAndroidInjector<Any>


    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        setActivityInjector()
    }

    override fun apply(
        base: Statement?,
        description: Description?
    ): Statement {
        return super.apply(base, description)
    }

    /**
     * Set dispatching back to previous object [TvApplication.activityDispatchingAndroidInjector]
     */
    override fun afterActivityFinished() {
        val testApp: RuangKajianApplication = ApplicationProvider.getApplicationContext()
        testApp.activityDispatchingAndroidInjector = tempDispatching
        super.afterActivityFinished()
    }

    /**
     * intercept dispatching android injector, to replace with mock
     */
    private fun setActivityInjector() {
        val testApp: RuangKajianApplication = ApplicationProvider.getApplicationContext()
        tempDispatching = testApp.activityDispatchingAndroidInjector
        testApp.activityDispatchingAndroidInjector =
            createFakeActivityInjector(activityClass, activityInjector)
    }

    private fun <T : Activity> createFakeActivityInjector(clazz: Class<T>, block: (T) -> Unit):
        DispatchingAndroidInjector<Any> {
        val injector = AndroidInjector<Any> { instance ->
            @Suppress("UNCHECKED_CAST")
            block(instance as T)
        }
        val factory = AndroidInjector.Factory<Any> { injector }
        val map =
            mapOf(Pair<Class<*>, Provider<AndroidInjector.Factory<*>>>(clazz, Provider { factory }))
        return DispatchingAndroidInjector_Factory.newInstance(map, emptyMap())
    }
}
