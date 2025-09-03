document.addEventListener("DOMContentLoaded", function () {
    let today = new Date().toISOString().split('T')[0];
    document.getElementById("dateInput").value = today;

    document.getElementById("btn-search").addEventListener("click", function () {
        const departure = document.getElementById("departure").value;
        const destination = document.getElementById("destination").value;

        if (!departure || !destination) {
            new Noty({
                type: "error",
                layout: "topRight",
                text: "Please select both departure and destination!",
                timeout: 2000
            }).show();
            return;
        }

        fetch(`http://localhost:8080/api/v1/JourneyPass/schedule/search3?departure=${departure}&destination=${destination}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("Fetched Data:", data);
                displaySchedules(data);
            })
            .catch(error => console.error("Error fetching schedules:", error));
    });
});

function displaySchedules(schedules) {
    const container = document.querySelector(".container.mt-4");
    document.querySelector(".about-section").style.display = "none";
    document.querySelector(".banner").style.display = "none";

    if (!container) {
        console.error("Error: .container.mt-4 element not found!");
        return;
    }

    container.innerHTML = "";

    if (schedules.length === 0) {
        container.innerHTML = "<p class=\"centered-message text-danger\">\n" +
            "  Sorry, there are no busses available right now in the route you searched.<br>\n" +
            "  Please try another date or change the Departure & Arrival point & search again.<br>\n" +
            "  Example:- Try Colombo - Badulla instead of Bambalapitiya - Badulla\n" +
            "</p>";
        return;
    }

    schedules.forEach(schedule => {
        const cardHTML = `
      <div class="card shadow-lg mb-3">
          <div class="card-header text-white d-flex justify-content-between">
              <h5>Stops @ ${schedule.departure}</h5>
              <span>Route # ${schedule.routeNumber || "N/A"}</span>
          </div>
          <div class="card-body">
              <div class="row">
                  <div class="col-md-3 text-center">
                      <img src="assets/images/470796840_1138306837825342_9180936747334891449_n.jpg" class="img-thumbnail" alt="Bus Image" style="max-width: 150px;">
                  </div>
                  <div class="col-md-6">
                      <div class="d-flex justify-content-between">
                          <div>
                              <h6 class="fw-bold">Departure</h6>
                              <p class="mb-0">${schedule.departure}</p>
                              <small class="text-muted">Departure Time: <b>${schedule.departureTime || "N/A"}</b></small>
                          </div>
                          <div class="text-center">
                              <h1 class="fw-bold text-warning">➤</h1>
                              <p><b>Duration:</b> ${schedule.duration || "N/A"}</p>
                          </div>
                          <div class="text-end">
                              <h6 class="fw-bold">Arrival</h6>
                              <p class="mb-0">${schedule.destination}</p>
                              <small class="text-muted">Arrival Time: <b>${schedule.arrivalTime || "N/A"}</b></small>
                          </div>
                      </div>
                  </div>
                  <div class="col-md-3">
                      <p><b>Bus Type:</b> ${schedule.busType || "N/A"}</p>
                      <p><b>Bus Number:</b> ${schedule.busNumber || "N/A"}</p>
                      <p><b>Schedule Id:</b> ${schedule.scheduleId || "N/A"}</p>
                  </div>
              </div>
          </div>
          <div class="card-footer d-flex justify-content-between align-items-center bg-dark text-white">
              <p class="mb-0"><b>Booking Closing Time:</b> ${calculateClosingTime(schedule.departureTime)}</p>
              <h4 class="text-warning">Rs. ${schedule.price || "N/A"}</h4>
              <p class="mb-0"><b>Available Seats:</b> <span class="text-danger">${schedule.availableSeats || "N/A"}</span></p>
              <button class="btn btn-warning bookingSeatBtn" 
              data-schedule-id="${schedule.scheduleId}" data-price="${schedule.price}">Book Seat</button>
          </div>
      </div>
    `;
        container.innerHTML += cardHTML;
    });
}
function calculateClosingTime(departureTime) {
    if (!departureTime) return "N/A";

    let departureDate = new Date(`1970-01-01T${departureTime}`);
    departureDate.setHours(departureDate.getHours() - 1);

    return departureDate.toTimeString().split(" ")[0];
}

document.addEventListener('click', function (e) {
    if (e.target && e.target.classList.contains('bookingSeatBtn')) {
        const seatSection = document.getElementById('seat-section');

        const scheduleId = e.target.getAttribute('data-schedule-id');
        const price = e.target.getAttribute('data-price');

        document.getElementById('scheduleId').innerText = `Schedule Id: ${scheduleId}`;
        document.getElementById('price').innerText = `Price: Rs. ${price}`;

        if (seatSection) {
            seatSection.style.display = 'block';
            document.querySelector(".banner").style.display = "none";
            document.querySelector(".cd").style.display = "none";
            document.querySelector(".login-container").style.display="none"
            seatSection.scrollIntoView({ behavior: 'smooth' });
        }
    }
});
