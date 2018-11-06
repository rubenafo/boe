# BOE retrieve and store
Fetch and store the spanish BOE (Boletin Oficial del Estado) in Amazon Web Services (AWS).    
The BOE summary is stored along with all the referenciated documents (BOE items) in it.

# Requirements
* An AWS account 
* DynamoDb containing two tables: 
  * boeContent: stores the BOE summaries
  * boeDocs: stores the documents linked to a BOE summary.

## Entry point
The class DbManager contains the main methods:
* fetchBoe (String date) : fetches a boe by date
* fetchBoeByMonth (int year, int month): fetches all the boe documents for a given month
