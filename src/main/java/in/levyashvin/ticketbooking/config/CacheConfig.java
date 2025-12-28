package in.levyashvin.ticketbooking.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Annotation is enough for redis-data to handle caching
}
