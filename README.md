# ExchangeApp
Demo app to show the use of Retrofit along with Recycler Views and patterns as Singleton &amp; MVVM.

You work for GNB (Goliath National Bank), and your manager, Barney Stinson, has asked you to design and implement a mobile application to help the firm executives who are always flying around the globe. Your executives need a list of every product GNB trades with, and the total sum of sales of those products in different currencies.

For this task you have to use a web service. This web service will provide its results in JSON format. The web service provides you with this set of data:

http://gnb.dev.airtouchmedia.com/rates.json will return you a document with the following formats: JSON [ { "from": "EUR", "to": "USD", "rate": "1.359" }, { "from": "CAD", "to": "EUR", "rate": "0.732" }, { "from": "USD", "to": "EUR", "rate": "0.736" }, { "from": "EUR", "to": "CAD", "rate": "1.366" } ] Each dictionary from the array specifies the conversion rate from one currency to another (when the direct conversion is given, the reverse one is also provided), but some conversions may not be specified, and in case they are needed, they will have to be calculated using the already known conversions. For example, in the sample data we don’t provide the USD to CAD conversion, that should be calculated from the USD to EUR and the EUR to CAD conversions.

http://gnb.dev.airtouchmedia.com/transactions.json will return you a document with the following formats: JSON [ { "sku": "T2006", "amount": "10.00", "currency": "USD" }, { "sku": "M2007", "amount": "34.57", "currency": "CAD" }, { "sku": "R2008", "amount": "17.95", "currency": "USD" }, { "sku": "T2006", "amount": "7.63", "currency": "EUR" }, { "sku": "B2009", "amount": "21.23", "currency": "USD" } ] Each dictionary represents a transaction of a given product (indicated by the product SKU) in the given currency for the given amount. Your application should download this information upon starting and give the user the choice of which product they want to see. When the user selects a product, the application must show each of the transactions related to that product and a sum of all the transactions transformed into EUR. For example, for the sample data, the total sum for the product T2006 should be 14,99 EUR.

Requirements

Do not block the UI while downloading the data from the network. Do not download the data from the network before showing the UI.
You can use any framework provided by the device, and third party libraries.
Remember that some conversion rates are missing, and they should be derived using the information provided.
