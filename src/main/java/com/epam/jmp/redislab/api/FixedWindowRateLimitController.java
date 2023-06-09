package com.epam.jmp.redislab.api;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.epam.jmp.redislab.service.RateLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ratelimit/fixedwindow")
public class FixedWindowRateLimitController {

    private final RateLimitService rateLimitService;

    public FixedWindowRateLimitController(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @PostMapping
    public ResponseEntity<Void> shouldRateLimit(@RequestBody RateLimitRequest rateLimitRequest) {
        if (rateLimitService.shouldLimit(rateLimitRequest.getDescriptors())) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public RateLimitRequest shouldRateLimit() {
        Set res = new HashSet<>();
                res.add(new RequestDescriptor(Optional.of("1"), Optional.of("2"), Optional.of("3")));
                res.add(new RequestDescriptor(Optional.of("2"), Optional.of("3"), Optional.of("4")));
        return new RateLimitRequest(res);
    }

}
