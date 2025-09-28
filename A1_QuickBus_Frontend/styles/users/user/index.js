document.getElementById("btn-timeTable").addEventListener("click", function () {

    document.querySelector(".search-box").style.display = "none";
    document.querySelector(".about-section").style.display = "none";
    const searchSection = document.querySelector(".search-section");
    searchSection.style.display = "block";

    searchSection.style.position = "absolute";
    searchSection.style.top = "35%";
    searchSection.style.left = "50%";
    searchSection.style.transform = "translate(-50%, -50%)";
    searchSection.style.background = "#195193";
    searchSection.style.padding = "20px";
    searchSection.style.borderRadius = "10px";
    searchSection.style.width = "80%";
    searchSection.style.maxWidth = "1000px";
});