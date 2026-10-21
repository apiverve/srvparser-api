# SRV Record Parser API - Ruby Gem

SRV Record Parser analyzes DNS SRV records and extracts service location information including priority, weight, port, and target server details.

## Installation

Add this line to your application's Gemfile:

```ruby
gem 'apiverve_srvparser'
```

And then execute:

```bash
$ bundle install
```

Or install it yourself as:

```bash
$ gem install apiverve_srvparser
```

## Getting Started

Get your API key at [APIVerve](https://apiverve.com)

### Basic Usage

```ruby
require 'apiverve_srvparser'

# Initialize the client
client = APIVerve::Srvparser::Client.new(api_key: "YOUR_API_KEY")

# Make a request
response = client.execute({ record: "_http._tcp.example.com. 86400 IN SRV 10 60 80 server.example.com." })

# Print the response
puts response
```


### Error Handling

```ruby
begin
  response = client.execute({ record: "_http._tcp.example.com. 86400 IN SRV 10 60 80 server.example.com." })
  puts response["data"]
rescue APIVerve::Srvparser::ValidationError => e
  puts "Validation error: #{e.errors.join(', ')}"
rescue APIVerve::Srvparser::APIError => e
  puts "API error: #{e.message}"
  puts "Status code: #{e.status_code}"
end
```

### Debug Mode

```ruby
# Enable debug logging
client = APIVerve::Srvparser::Client.new(
  api_key: "YOUR_API_KEY",
  debug: true
)
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

## Documentation

For more information, visit the [API Documentation](https://docs.apiverve.com/ref/srvparser?utm_source=rubygems&utm_medium=referral&utm_content=readme).

## Support

- Website: [https://apiverve.com/marketplace/srvparser?utm_source=ruby&utm_medium=referral&utm_content=readme](https://apiverve.com/marketplace/srvparser?utm_source=ruby&utm_medium=referral&utm_content=readme)
- Email: hello@apiverve.com

## License

This gem is available under the [MIT License](LICENSE).
