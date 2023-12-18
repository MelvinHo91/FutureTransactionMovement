# PROCESSED FUTURE MOVEMENT Project

## How to Run
* Make sure you are using JDK 17 and Maven 3.x
* Build and run the app using maven
```bash
mvn clean package
java -jar target/abmaro-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using

```bash
mvn spring-boot:run
```

## About the Service

### Generate a daily summary report

CSV data format:
* Client_Information: 
>**{CLIENT_TYPE}\_{CLIENT_NUMBER}\_{ACCOUNT_NUMBER}\_{SUBACCOUNT_NUMBER}**

Example: Client type is **CL**, client number is **1234**, account number is **0001**, subaccount number is **0002**, the represented data is **CL\_1234\_1\_2**
* Product_Information: 

>**{EXCHANGE_CODE}\_{PRODUCT_GROUP_CODE}\_{SYMBOL}\_{EXPIRATION_DATE}**

Example: Exchange code is **CME**, product group code is **FU**, symbol is **NK**, expiration date is **20100910**, the represented data is **CME\_FU\_NK\_20100910**

Sample request (cUrl)
```
curl --location 'http://localhost:8080/future-transactions/daily-summary-report' \
--form 'transactionRecord=@"{YOUR_TRANSACTION_RECORD_PATH}"'

RESPONSE: HTTP 200 (OK)
Content-Type: text/csv
Content-Disposition: attachment; filename=Output.csv

Client_Information,Product_Information,Total_Transaction_Amount
{CLIENT_INFORMATION},{PRODUCT_INFORMATION},{TOTAL_TRANSACTION_AMOUNT}
```