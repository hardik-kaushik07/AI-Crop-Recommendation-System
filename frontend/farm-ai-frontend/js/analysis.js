const form=document.getElementById("analysisForm");

const loading=document.getElementById("loading");

const resultCard=document.getElementById("resultCard");

form.addEventListener("submit",async(e)=>{

    e.preventDefault();

    loading.style.display="block";

    resultCard.style.display="none";

    const request={

        location:document.getElementById("location").value,

        soilType:document.getElementById("soilType").value,

        ph:Number(document.getElementById("ph").value),

        nitrogen:document.getElementById("nitrogen").value,

        phosphorus:document.getElementById("phosphorus").value,

        potassium:document.getElementById("potassium").value,

        season:document.getElementById("season").value

    };

    try{

        const token=localStorage.getItem("token");

        const response=await fetch(API_BASE_URL+"/api/ai/analyze",{

            method:"POST",

            headers:{

                "Content-Type":"application/json",
                "Authorization":"Bearer "+token
            },

            body:JSON.stringify(request)

        });

        if(!response.ok){

            throw new Error("Failed");

        }

        const data=await response.json();

        document.getElementById("crop").innerText=data.crop;

        document.getElementById("reason").innerText=data.reason;

        document.getElementById("fertilizer").innerText=data.fertilizer;

        document.getElementById("pesticide").innerText=data.pesticide;

        document.getElementById("irrigation").innerText=data.irrigation;

        document.getElementById("diseaseRisk").innerText=data.diseaseRisk;

        document.getElementById("harvestTime").innerText=data.harvestTime;

        document.getElementById("expectedYield").innerText=data.expectedYield;

        document.getElementById("temperature").innerText=data.temperature+" °C";

        document.getElementById("humidity").innerText=data.humidity+" %";

        document.getElementById("weather").innerText=data.weatherCondition;

        loading.style.display="none";

        resultCard.style.display="block";

        resultCard.scrollIntoView({

            behavior:"smooth"

        });

    }

    catch(error){

        loading.style.display="none";

        document.getElementById("result").innerHTML=
    "<h3 style='color:red'>Analysis Failed. Please try again.</h3>";

        console.log(error);

    }

});