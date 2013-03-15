import com.redhat.theses.auth.CustomUserDetailsService
import com.redhat.theses.config.Configuration

// Place your Spring DSL code here
beans = {

    userDetailsService(CustomUserDetailsService)

    configuration(Configuration) {
        configurationProvider = ref("mongoConfigurationProviderService")
    }
}
