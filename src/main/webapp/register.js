let backButton = document.getElementById("back-button");
backButton.addEventListener('click', (event) => {
event.preventDefault();
window.location.replace("index.html")
})



let registerButton = document.getElementById("register");
registerButton.addEventListener('click', async(event) =>{
    event.preventDefault();
    let email = document.getElementById("email-register").value;
    let passcode = document.getElementById("password-register").value;
    let firstname = document.getElementById("first-register").value;
    let lastname = document.getElementById("last-register").value;
    let birthday = document.getElementById("birthday").value;

    const myArray = birthday.split("-");
    let year = myArray[0];
    let month = myArray[1];
    let day = myArray[2];

    console.log(birthday);
    console.log("Sign: "+zodiac_sign(day, month));
    let sign = zodiac_sign(day, month);
    //console.log(sign);
    let registerInfo = {
        firstName:firstname,
        lastName:lastname,
        email:email,
        passcode:passcode,
        zodiac:sign
    }
    let json = JSON.stringify(registerInfo);
    try {
        const raw_response = await fetch(`http://localhost:8080/registerUser`,{
            method:"POST", // we hdd the http to be executed
            body:json
        });
        if(!raw_response.ok){
            throw new Error(raw_response.status)
        }
        raw_response.json().then( (data) => {
            let loggedInUser = JSON.stringify(data)
            localStorage.setItem("currentUser",loggedInUser)
            console.log(loggedInUser);
        })
        setTimeout( ()=>{
            window.location.replace("home.html")
        }, 2000 )
    }catch(error){
    console.log(error)
    }
})



function zodiac_sign(uday, umonth)
{
    let day = Number(uday);
    let month = Number(umonth);
    console.log("Day: "+day);
    console.log("Month: "+month);
    let sign="";
        if (month == 12){
            if (day < 22)
            sign = "Sagittarius";
            else
            sign ="Capricorn";
        }
        else if (month == 1){
            if (day < 20)
            sign = "Capricorn";
            else
            sign = "Aquarius";
        }
        else if (month == 2){
            if (day < 19)
            sign = "Aquarius";
            else
            sign = "Pisces";
        }
        else if(month == 3){
            if (day < 21)
            sign = "Pisces";
            else
            sign = "Aries";
        }
        else if (month == 4){
            if (day < 20)
            sign = "Aries";
            else
            sign = "Taurus";
        }
               
        else if (month == 5){
            if (day < 21)
            sign = "Taurus";
            else
            sign = "Gemini";
        }
               
        else if( month == 6){
            if (day < 21)
            sign = "Gemini";
            else
            sign = "Cancer";
        }
               
        else if (month == 7){
            if (day < 23)
            sign = "Cancer";
            else
            sign = "Leo";
        }
               
        else if( month == 8){
            if (day < 23)
            sign = "Leo";
            else
            sign = "Virgo";
        }
               
        else if (month == 9){
            if (day < 23)
            sign = "Virgo";
            else
            sign = "Libra";
        }
               
        else if (month == 10){
            if (day < 23)
            sign = "Libra";
            else
            sign = "Scorpio";
        }
               
        else if (month == 11){
            if (day < 22)
            sign = "Scorpio";
            else
            sign = "Sagittarius";
        }
        console.log(sign);
        return sign;
}