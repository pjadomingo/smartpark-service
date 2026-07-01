## Prerequisites
- Install [Java 17](https://adoptium.net/)
- Install [Maven](https://maven.apache.org/)
- Clone the repository:
  ```bash
  git clone https://github.com/pjadomingo/smartpark-service.git
  cd smartpark-service
- Run the app
  mvn clean install spring-boot:run
- Access the app
  http://localhost:8081/auth/login
  payload: {
    "email":"guest@example.com",
    "password":"guest123"
    }
- Register Vehicle
  POST http://localhot:8081/api/v1/vehicle
  payload: {
    licensePlate:<string>,
    ownerName:<string>,
    type: <string> Car | Motorcycle | Truck
  }
- Register Parking Lot
  POST http://localhot:8081/api/v1/parkinglot
  paylod:{
    lotId:<string>,
    location:<string>,
    capacity:<int>,
    occupiedSpaces:<int>,
    costPerMinute:<double>
  }
- Get Parking Lot
  GET  http://localhost:8081/api/v1/parkinglot/{lotId}
- Check In Vehicle Parking
  POST http://localhost:8081/api/v1/smartpark/checkIn/{lotId}/{licensePlate}
- Check Out Vehicle Parking
  POST http://localhost:8081/api/v1/smartpark/checkOut/{lotId}/{licensePlate}

NOTE: Need to authenticate using login API and add in the header the access token, there are already preloaded data for parking lot and vehicles. Kindly check data.sql
  
