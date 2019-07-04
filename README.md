Clone the repo on to your local drive

maven clean and install the repo 

to run the app mvn spring-boot:run

This application uses a h2 db
The db report script is under \src\main\resources\script.sql
To login and run scripts 
  http://localhost:8102/h2-console/
under the url : jdbc:h2:mem:bank_discovery


how to run the application the api are as follows

    To get the currency accounts
      -http://localhost:8102/accounts/getCurrencyValueWithAccounts/{id}

    To get the transactional accounts 
      -http://localhost:8102/accounts/getAllTransactionalAccs/{id}
      
    To make a withdrawal 
      -http://localhost:8102/accounts/makeAWithdrawalll/{atm_id}/{client_id}/{client_account}/{amount}

    
