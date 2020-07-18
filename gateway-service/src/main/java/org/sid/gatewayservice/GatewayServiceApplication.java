package org.sid.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableHystrix
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    // ********************* Configuration statique **************************

    // configuration de la route quand le service d enregistrement n est pas encore la

    /*    @Bean
    RouteLocator staticRoutes (RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/customers/**").uri("http://localhost:8081/").id("r1"))
                .route(r->r.path("/products/**").uri("http://localhost:8082/").id("r2"))
                .build();
    }*/

    // ********************* Configuration statique **************************
    // Configuration avec le service d enregistrement

/*
    @Bean
    RouteLocator staticRoutes (RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE").id("r1"))
                .route(r->r.path("/products/**").uri("lb://INVENTORY-SERVICE").id("r2"))
                .build();
    }
*/

    // ********************* Configuration dynamique **************************

    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){

        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }

    // ********************* Configuration statique pour appeler les api externes **************************

    @Bean
    RouteLocator staticRoutes (RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/publicCountries/**")
                        .filters(f -> f
                                .addRequestHeader("x-rapidapi-host","restcountries-v1.p.rapidapi.com")
                                .addRequestHeader("x-rapidapi","key-value")
                                .rewritePath("/publicCountries/(?<segment>.*)","/${segment}")
                                // rediriger vers l url de defaut si l url n est pas disponible
                                .hystrix(h->h.setName("countries").setFallbackUri("forward:/defaultCountries")))
                        .uri("http://restcountries-v1.p.rapidapi.com").id("r1"))

                .route(r->r.path("/muslim/**")
                        .filters(f -> f
                                .addRequestHeader("x-rapidapi-host","muslimsalat.p.rapidapi.com")
                                .addRequestHeader("x-rapidapi-key","key-value")
                                .rewritePath("/muslim/(?<segment>.*)","/${segment}"))
                        .uri("http://muslimsalat.p.rapidapi.com").id("r2"))

                .build();
    }

}

@RestController
class CircuitBreakerRestController{

    @GetMapping("/defaultCountries")
    public Map<String,String> countries(){
        Map<String,String> data = new HashMap<>();
        data.put("message", "default Countries");
        data.put("countries","Maroc,Algerie,Tunisie,.....");

        return data;
    };


}
