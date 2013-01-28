import com.redhat.theses.auth.CustomUserDetailsService
import com.redhat.theses.config.Configuration
import com.redhat.theses.config.DummyConfigurationProvider

// Place your Spring DSL code here
beans = {

    userDetailsService(CustomUserDetailsService)

    configurationProvider(DummyConfigurationProvider)

    configuration(Configuration) {
        configurationProvider = ref("configurationProvider")
    }
}
