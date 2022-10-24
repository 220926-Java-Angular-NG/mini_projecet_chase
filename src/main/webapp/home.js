let currentUser = localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);
console.log(currentUser);

let welcomeLabel = document.getElementById("welcome-label");
welcomeLabel.innerHTML = `Welcome ${currentUser.firstName}! Here is your horoscope!`

let name = document.getElementById("name");
name.innerHTML="Name: "+currentUser.firstName +" " +currentUser.lastName;
let email = document.getElementById("email");
email.innerHTML="Email: "+currentUser.email;
let sign = document.getElementById("sign");
sign.innerHTML="Sign: "+currentUser.zodiac;

getTodaysHoroscope();

async function getTodaysHoroscope(){
    try {
        let text = `${currentUser.zodiac}`;
        let path = text.toLowerCase();
        const raw_response =await fetch("http://sandipbgt.com/theastrologer/api/horoscope/"+path+"/today");
        if(!raw_response.ok){
            throw new Error(raw_response.status);
        }
        const json_data = await raw_response.json();
        console.log(json_data);
        let todaysScope = document.getElementById("todaysScope");
        todaysScope.innerHTML=json_data.horoscope;
    } catch (error) {
        console.log(error)
    }
}

let mood = document.getElementById("requestmood");
mood.innerHTML="Mood: ";



let requestMood = document.getElementById("request-mood-button");
requestMood.addEventListener('click', async(event) =>{
    event.preventDefault();
    try {
        let text = `${currentUser.zodiac}`;
        let path = text.toLowerCase();
        const raw_response =await fetch("http://sandipbgt.com/theastrologer/api/horoscope/"+path+"/today");
        if(!raw_response.ok){
            throw new Error(raw_response.status);
        }
        const json_data = await raw_response.json();
        console.log(json_data);
        mood.innerHTML="Mood: "+json_data.meta.mood;
    } catch (error) {
        console.log(error)
    }
})
