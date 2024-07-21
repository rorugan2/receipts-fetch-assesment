# Receipt Points Calculator

This is a Spring Boot application that calculates points for receipts based on specific rules.

## Features
- Store receipts in memory.
- Calculate points for each receipt based on predefined rules.

## Technologies Used
- Java
- Spring Boot
- Maven

## Prerequisites
- Java 11 or higher
- Maven 3.6.3 or higher

## Installation

### 1. Clone the Repository
```sh
git clone https://github.com/rorugan2/receipt-points-calculator.git
cd receipt-points-calculator
```

### 2. Build the Application
Make sure you have Maven installed and in your PATH. Run the following command in the root directory of the project:
```sh
mvn clean install
```

### 3. Run the Application
You can run the application using the following Maven command:
```sh
mvn spring-boot:run
```


## Usage

### API Endpoints

#### Add a Receipt
- **URL**: `/api/receipts/process`
- **Method**: `POST`
- **Request Body**: JSON
  ```json
  {
    "retailer": "Target",
    "purchaseDate": "2022-01-01",
    "purchaseTime": "13:01",
    "items": [
      {
        "shortDescription": "Mountain Dew 12PK",
        "price": "6.49"
      },
      {
        "shortDescription": "Emils Cheese Pizza",
        "price": "12.25"
      },
      {
        "shortDescription": "Knorr Creamy Chicken",
        "price": "1.26"
      },
      {
        "shortDescription": "Doritos Nacho Cheese",
        "price": "3.35"
      },
      {
        "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
        "price": "12.00"
      }
    ],
    "total": "35.35"
  }
  ```
- **Response**: JSON with the receipt ID
  ```json
  {
    "id": "unique-receipt-id"
  }
  ```

#### Get Receipt Points
- **URL**: `/api/receipts/{id}/points`
- **Method**: `GET`
- **Response**: JSON with the total points
  ```json
  {
    "points": 28
  }
  ```

## Project Structure
```plaintext
receipt-points-calculator/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── receipt/
│   │   │               ├── controller/
│   │   │               │   └── ReceiptController.java
│   │   │               ├── model/
│   │   │               │   ├── Id.java
│   │   │               │   ├── Item.java
│   │   │               │   ├── Points.java
│   │   │               │   └── Receipt.java
│   │   │               ├── service/
│   │   │               │   └── ReceiptService.java
│   │   │               └── ReceiptsApplication.java
│   │   └── resources/
│   │       └── application.properties
├── target/
├── .gitignore
├── README.md
└── pom.xml
```

## Example Requests

### Add a Receipt
```sh
curl -X POST http://localhost:8080/api/receipts \
     -H "Content-Type: application/json" \
     -d '{
           "retailer": "Target",
           "purchaseDate": "2022-01-01",
           "purchaseTime": "13:01",
           "items": [
             {"shortDescription": "Mountain Dew 12PK", "price": "6.49"},
             {"shortDescription": "Emils Cheese Pizza", "price": "12.25"},
             {"shortDescription": "Knorr Creamy Chicken", "price": "1.26"},
             {"shortDescription": "Doritos Nacho Cheese", "price": "3.35"},
             {"shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ", "price": "12.00"}
           ],
           "total": "35.35"
         }'
```

### Get Receipt Points
```sh
curl -X GET http://localhost:8080/api/receipts/{id}/points
```

Replace `{id}` with the actual receipt ID you receive from the add receipt response.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---