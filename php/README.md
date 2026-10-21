# SRV Record Parser API - PHP Package

SRV Record Parser analyzes DNS SRV records and extracts service location information including priority, weight, port, and target server details.

## Installation

Install via Composer:

```bash
composer require apiverve/srvparser
```

## Getting Started

Get your API key at [APIVerve](https://apiverve.com)

### Basic Usage

```php
<?php

require_once 'vendor/autoload.php';

use APIVerve\Srvparser\Client;

// Initialize the client
$client = new Client('YOUR_API_KEY');

// Make a request
$response = $client->execute(['record' => '_http._tcp.example.com. 86400 IN SRV 10 60 80 server.example.com.']);

// Print the response
print_r($response);
```


### Error Handling

```php
use APIVerve\Srvparser\Client;
use APIVerve\Srvparser\Exceptions\APIException;
use APIVerve\Srvparser\Exceptions\ValidationException;

try {
    $response = $client->execute(['record' => '_http._tcp.example.com. 86400 IN SRV 10 60 80 server.example.com.']);
    print_r($response['data']);
} catch (ValidationException $e) {
    echo "Validation error: " . implode(', ', $e->getErrors());
} catch (APIException $e) {
    echo "API error: " . $e->getMessage();
    echo "Status code: " . $e->getStatusCode();
}
```

### Debug Mode

```php
// Enable debug logging
$client = new Client(
    apiKey: 'YOUR_API_KEY',
    debug: true
);
```

## Example Response

```json
{
  "status": "ok",
  "error": null,
  "data": {
    "raw_record": "_http._tcp.example.com. 86400 IN SRV 10 60 80 server.example.com.",
    "parsed": {
      "name": "_http._tcp.example.com.",
      "service": "_http",
      "protocol": "tcp",
      "domain": "example.com.",
      "ttl": 86400,
      "class": "IN",
      "priority": 10,
      "weight": 60,
      "port": 80,
      "target": "server.example.com"
    },
    "service_info": {
      "name": "HTTP",
      "description": "Web service",
      "default_port": 80
    },
    "interpretation": {
      "priority_explanation": "Priority level 10 (lower is better)",
      "weight_explanation": "Weight 60 for load balancing",
      "target_explanation": "Connect to server.example.com:80"
    },
    "is_valid": true
  }
}
```

## Requirements

- PHP 7.4 or higher
- Guzzle HTTP client

## Documentation

For more information, visit the [API Documentation](https://docs.apiverve.com/ref/srvparser?utm_source=packagist&utm_medium=referral&utm_content=readme).

## Support

- Website: [https://apiverve.com/marketplace/srvparser?utm_source=php&utm_medium=referral&utm_content=readme](https://apiverve.com/marketplace/srvparser?utm_source=php&utm_medium=referral&utm_content=readme)
- Email: hello@apiverve.com

## License

This package is available under the [MIT License](LICENSE).
