const BASE_URL = "http://localhost:8779/api/v1/account"

function createAccount() {

    const URL = `${BASE_URL}/create`;
    let reqObj = {accountName: "", phoneNo: ""}

    const httpRequest = new XMLHttpRequest();
    let accountName = document.getElementById("accountName").value
    let phoneNo = document.getElementById("phoneNo").value
    reqObj.accountName = accountName;
    reqObj.phoneNo = phoneNo;
    console.log(reqObj);
    console.log(JSON.stringify(reqObj))
    httpRequest.onload = function () {
        getAccountList();
    }
    httpRequest.open("POST", URL);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(JSON.stringify(reqObj));

}

function deposit() {

    const URL = `${BASE_URL}/deposit`;
    // const httpRequest = new XMLHttpRequest();

    let accountNo = document.getElementById("withdrawal-accountNo").value
    let amount = document.getElementById("withdrawal-amount").value
    let reqObj = {accountNo:accountNo, amount: amount}

    fetch(`${URL}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reqObj),
    })
        .then((res) => res.json())
        .then((data) => {
            console.log(data)

        })
        .catch((err) => {
            window.alert(err);
            console.log(err)
        });
    // reqObj.accountNo = accountNo;
    // reqObj.amount = amount;
    // console.log(reqObj);
    // console.log(JSON.stringify(reqObj))
    // httpRequest.onload = function () {
    //     getAccountList();
    // }
    // httpRequest.open("POST", URL);
    // httpRequest.setRequestHeader("Content-type", "application/json");
    // httpRequest.send(JSON.stringify(reqObj));

}

function withdraw(accountNo, amount) {

    const URL = `${BASE_URL}/withdraw`;
    let reqObj = {accountNo: accountNo, amount: amount}


    fetch(`${URL}}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reqObj),
    })
        .then((res) => res.json())
        .then((data) => {
            console.log(data)

        })
        .catch((err) => console.log(err));

    // const httpRequest = new XMLHttpRequest();
    // let accountNo = document.getElementById("accountNo").value
    // let amount = document.getElementById("amount").value
    // reqObj.accountNo = accountNo;
    // reqObj.amount = amount;
    // console.log(reqObj);
    // console.log(JSON.stringify(reqObj))
    // httpRequest.onload = function () {
    //     getAccountList();
    // }
    // httpRequest.open("POST", URL);
    // httpRequest.setRequestHeader("Content-type", "application/json");
    // httpRequest.send(JSON.stringify(reqObj));

}

