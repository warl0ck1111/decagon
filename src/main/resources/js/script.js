
    window.document.onload =function (e){
    getAccountList();
}
    const BASE_URL = "http://localhost:8779/api/v1/account"

    function createAccount() {

    const URL = `${BASE_URL}/create`;
    let reqObj = {accountName:"", phoneNo:""}

    const httpRequest = new XMLHttpRequest();
    let accountName = document.getElementById("accountName").value
    let phoneNo = document.getElementById("phoneNo").value
    reqObj.accountName = accountName;
    reqObj.phoneNo = phoneNo;
    console.log(reqObj);
    console.log(JSON.stringify(reqObj))
    httpRequest.onload = function() {
    getAccountList();
}
    httpRequest.open("POST", URL);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(JSON.stringify(reqObj));

}

    function deposit() {

    const URL = `${BASE_URL}/deposit`;
    let reqObj = {accountNo:"", amount:""}

    const httpRequest = new XMLHttpRequest();
    let accountNo = document.getElementById("accountNo").value
    let amount = document.getElementById("amount").value
    reqObj.accountNo = accountNo;
    reqObj.amount = amount;
    console.log(reqObj);
    console.log(JSON.stringify(reqObj))
    httpRequest.onload = function() {
    getAccountList();
}
    httpRequest.open("POST", URL);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(JSON.stringify(reqObj));

}

    function withdraw() {

    const URL = `${BASE_URL}/withdraw`;
    let reqObj = {accountNo:"", amount:""}

    const httpRequest = new XMLHttpRequest();
    let accountNo = document.getElementById("accountNo").value
    let amount = document.getElementById("amount").value
    reqObj.accountNo = accountNo;
    reqObj.amount = amount;
    console.log(reqObj);
    console.log(JSON.stringify(reqObj))
    httpRequest.onload = function() {
    getAccountList();
}
    httpRequest.open("POST", URL);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(JSON.stringify(reqObj));

}

    //########################################### ACCOUNT LIST ###############################################################
    let accountTableHeaders = ["Account Name", "Account No", "Phone No", "Balance"]
    const accountListDiv = document.querySelector("div.accountList") // Find the accountList div in our html
    const createAccountListTable = () => {
    while (accountListDiv.firstChild) accountListDiv.removeChild(accountListDiv.firstChild) // Remove all children from accountList div (if any)
    let accountListTable = document.createElement('table') // Create the table itself
    accountListTable.className = 'accountListTable'
    let accountListHead = document.createElement('thead') // Creates the table header group element
    accountListHead.className = 'accountListHead'
    let accountListTableHeaderRow = document.createElement('tr') // Creates the row that will contain the headers
    accountListTableHeaderRow.className = 'accountListTableHeaderRow'
// Will iterate over all the strings in the tableHeader array and will append the header cells to the table header row
    accountTableHeaders.forEach(header => {
    let accountListHeader = document.createElement('th') // Creates the current header cell during a specific iteration
    accountListHeader.innerText = header
    accountListTableHeaderRow.append(accountListHeader) // Appends the current header cell to the header row
})
    accountListHead.append(accountListTableHeaderRow) // Appends the header row to the table header group element
    accountListTable.append(accountListHead)
    let accountListTableBody = document.createElement('tbody') // Creates the table body group element
    accountListTableBody.className = "accountListTable-Body"
    accountListTable.append(accountListTableBody) // Appends the table body group element to the table
    accountListDiv.append(accountListTable) // Appends the table to the accountList div
}

    // The function below will accept a single account
    const appendAccounts = (singleAccount) => {
    const accountListTable = document.querySelector('.accountListTable') // Find the table  created
    let accountListTableBodyRow = document.createElement('tr') // Create the current table row
    accountListTableBodyRow.className = 'accountListTableBodyRow'

//  create the 4 column cells that will be appended to the current table row
    let accountName = document.createElement('td')
    accountName.innerText = singleAccount.accountName

    let accountNumber = document.createElement('td')
    accountNumber.innerText = singleAccount.accountNumber

    let phoneNo = document.createElement('td')
    phoneNo.innerText = singleAccount.phoneNo

    let balance = document.createElement('td')
    balance.innerText = singleAccount.balance

    accountListTableBodyRow.append(phoneNo, accountName, accountNumber, balance) // Append all 4 cells to the table row
    accountListTable.append(accountListTableBodyRow) // Append the current row to the accountList table body
}

    const getAccountList = () => {
    fetch(`${BASE_URL}/accounts`) // Fetch for all accounts. The response is an array of objects
        .then(res => res.json())
        .then(accounts => {
            createAccountListTable() // Clears accountList div if it has any children nodes, creates & appends the table
// Iterates through all the objects in the accounts array and appends each one to the table body

            for (let index in accounts) {
                appendAccounts(accounts[index]) // Creates and appends each row to the table body

            }
        })
}

    //##########################################################################################################
    //####################################### TRANSACTION LIST ###################################################################

    let tableHeaders = ["Account Name", "Account Number", "Amount", "Transaction Type", "Created At"]
    const transactionListDiv = document.querySelector("div.transactionList") // Find the transactionList div in the html

    const createTransactionListTable = () => {
    while (transactionListDiv.firstChild) transactionListDiv.removeChild(transactionListDiv.firstChild) // Remove all children from transactionList div (if any)
    let transactionListTable = document.createElement('table') // Create the table itself
    transactionListTable.className = 'transactionListTable'
    let transactionListHead = document.createElement('thead') // Creates the table header group element
    transactionListHead.className = 'transactionListHead'
    let transactionListTableHeaderRow = document.createElement('tr') // Creates the row that will contain the headers
    transactionListTableHeaderRow.className = 'transactionListTableHeaderRow'
// Will iterate over all the strings in the tableHeader array and will append the header cells to the table header row
    tableHeaders.forEach(header => {
    let transactionListHeader = document.createElement('th') // Creates the current header cell during a specific iteration
    transactionListHeader.innerText = header
    transactionListTableHeaderRow.append(transactionListHeader) // Appends the current header cell to the header row
})
    transactionListHead.append(transactionListTableHeaderRow) // Appends the header row to the table header group element
    transactionListTable.append(transactionListHead)
    let transactionListTableBody = document.createElement('tbody') // Creates the table body group element
    transactionListTableBody.className = "transactionListTable-Body"
    transactionListTable.append(transactionListTableBody) // Appends the table body group element to the table
    transactionListDiv.append(transactionListTable) // Appends the table to the transactionList div
}

    // The function below will accept a single transaction
    const appendTransactions = (singleTransaction) => {
    const transactionListTable = document.querySelector('.transactionListTable') // Find the table created
    let transactionListTableBodyRow = document.createElement('tr') // Create the current table row
    transactionListTableBodyRow.className = 'transactionListTableBodyRow'
// Lines 72-85 create the 5 column cells that will be appended to the current table row

    let accountName = document.createElement('td')
    accountName.innerText = JSON.stringify(singleTransaction.accountName)

    let accountNumber = document.createElement('td')
    accountNumber.innerText = singleTransaction.accountNumber

    let amount = document.createElement('td')
    amount.innerText = singleTransaction.amount

    let transactionType = document.createElement('td')
    transactionType.innerText = singleTransaction.transactionType

    let createdAt = document.createElement('td')
    createdAt.innerText = singleTransaction.createdAt

    transactionListTableBodyRow.append(accountName, accountNumber, amount, transactionType, createdAt) // Append all 5 cells to the table row
    transactionListTable.append(transactionListTableBodyRow) // Append the current row to the transactionList table body
}

    const getTransactions = () => {
    let accountNoKey = document.getElementById("search-account").value;
    let TRANSACTIONS_LIST_URL = `${BASE_URL}/transactions?accountNo=${accountNoKey? accountNoKey : ""}`
    fetch(TRANSACTIONS_LIST_URL) // Fetch for all transactions. The response is an array of objects that is sorted by the most recent transaction
    .then(res => res.json())
    .then(transactions => {
    createTransactionListTable() // Clears transactionList div if it has any children nodes, creates & appends the table

// Iterates through all the objects in the transactions array and appends each one to the table body
    for (let index in transactions) {
    appendTransactions(transactions[index]) // Creates and appends each row to the table body
    console.log(transactions[index])
}
})
}


    //##########################################################################################################