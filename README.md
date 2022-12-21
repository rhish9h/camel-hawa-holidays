# Camel Hawa Holidays

## 3 Layered Spring Boot based application using integration framework Apache Camel to retrieve holiday packages from different vendors

The client has access to the experience layer API endpoint. Based on source and destination, holiday packages can be 
retrieved. Holiday packages will have data related to Cabs, Hotels, Flights, Railways.

Following are the 3 layers:

1. Experience Layer
    - Below REST end point is exposed. This can be called to fetch holiday packages for specific source and destination.
      - `/packages?source={source}&destination={destination}`
    - This will further call the APIs in the process layer.
    - This is the only layer exposed to the end client.
2. Process Layer
    - Below REST end point is exposed. This can be called to fetch holiday packages for specific source and destination.
      - `/packages?source={source}&destination={destination}`
    - This will further call the APIs in the system layer. It collects data related to Cabs, Hotels, Flights, Railways
   from individual vendors and aggregates them into a list of packages using aggregation business logic.
    - This layer will be exposed to APIs in the experience layer.
3. System Layer
   - Below REST end points are exposed. These can be called to fetch specific data related to Cabs, Hotels, Flights, Railways.
     - `/cabs?destination={destination}`
     - `/flights?source={source}&destination={destination}`
     - `/hotels?city={city}`
     - `/railways?source={source}&destination={destination}`
   - This layer will be exposed to APIs in the process layer.

```
Note: All three layers are meant to be deployed separately
```