
Markdown
# Currency Converter API

## Overview
This is a Spring Boot application that integrates with a public API to provide real-time currency conversion functionality. The application fetches real-time currency exchange rates and provides endpoints to convert amounts from one currency to another.

## Requirements
- Java 11 or later
- Maven 3.6.3 or later

## Setup and Running the Application

1. **Clone the repository:**
   ```sh
   git clone <https://github.com/Kulbirr/Currency-Converter-Api.git>
Navigate to the project directory:

sh
cd Currency-Converter-Api
Build the application using Maven:

sh
mvn clean install
Run the application:

sh
mvn spring-boot:run
The application will start and be accessible at http://localhost:8080.

Configuration
The application fetches exchange rates from a free public API. You need to configure the API URL in the application.properties file located in the src/main/resources directory:

properties
currency.api.url=https://api.frankfurter.dev/v1/
API Endpoints
1. Fetch Exchange Rates
   base is optional if you dont provide base it will take USD as default
GET /api/rates?base=USD
Fetch the exchange rates for the given base currency. If the base currency is not provided, it defaults to USD.

Example Request:
sh
curl -X GET "http://localhost:8080/api/rates?base=USD"
Example Response:

JSON
{
    "EUR": 0.845,
    "GBP": 0.75,
    "INR": 74.85,
    ...
  }
  
2. Convert Currency
POST /api/convert

Convert an amount from one currency to another using the fetched exchange rates.
Request Body:

JSON
{
  "from": "USD",
  "to": "EUR",
  "amount": 100
}
Example Request:

sh
curl -X POST "http://localhost:8080/api/convert" -H "Content-Type: application/json" -d '{"from": "USD", "to": "EUR", "amount": 100}'
Example Response:

JSON
{
  "from": "USD",
  "to": "INR",
  "amount": 1,
  "convertedAmount": 84.56
}
Error Handling
The application handles the following cases:

The external API is unavailable.
Invalid currency codes are provided.
Testing
Basic unit tests are written for the service layer using JUnit. To run the tests, use the following Maven command:

sh
mvn test
Project Structure
Code
currency-converter-api
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.Currency.Converter.API
│   │   │       ├── Controller
│   │   │       │   └── CurrencyController.java
│   │   │       ├── DTO
│   │   │       │   └── CurrencyConversionRequest.java
│   │   │       ├── Response
│   │   │       │   └── CurrencyApiResponse.java
│   │   │       │   └── CurrencyConversionResponse.java
│   │   │       ├── Service
│   │   │       │   ├── CurrencyService.java
│   │   │       │   └── CurrencyServiceImpl.java
│   │   │       └── CustomExceptions
│   │   │           ├── ApiNotAvailableException.java
│   │   │           └── InvalidCurrencyCodeException.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com.example.Currency.Converter.API
│               └── Service
│                   └── CurrencyServiceImplTest.java
│
└── pom.xml
License
This project is licensed under the MIT License.

Contact
For any queries or issues, please contact kulbirbhandari04@gmail.com.

Code
This `README.md` file provides a clear overview of the project, setup instructions, API endpoints and err
