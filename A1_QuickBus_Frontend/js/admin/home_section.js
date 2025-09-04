// Get bus Count
$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/journeyPass/bus/count",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken"),
            "Content-Type": "application/json"
        },
        success: function (response) {
            $("#homeBusCount").text(response);
        },
        error: function (xhr, status, error) {
            console.error("Error fetching bus count:", error);
            $("#homeBusCount").text("Error");
        }
    });
});

// Get route Count
$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/JourneyPass/route/count",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken"),
            "Content-Type": "application/json"
        },
        success: function (response) {
            $("#homeRouteCount").text(response);
        },
        error: function (xhr, status, error) {
            console.error("Error fetching route count:", error);
            $("#homeBusCount").text("Error");
        }
    });
});

// Get schedule Count
$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/JourneyPass/schedule/count", // API Endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken"),
            "Content-Type": "application/json"
        },
        success: function (response) {
            $("#homeSchedulesCount").text(response); // Update h2 with the bus count
        },
        error: function (xhr, status, error) {
            console.error("Error fetching schedule count:", error);
            $("#homeSchedulesCount").text("Error"); // Display error message if request fails
        }
    });
});


$(document).ready(function () {
    loadPassengers();

    function loadPassengers() {
        $.ajax({
            url: 'http://localhost:8080/api/v1/JourneyPass/passenger/getAll',
            method: 'GET',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            success: function (passengers) {
                const tbody = $('#schedule-passenger-tbody');
                tbody.empty(); // Clear old data

                passengers.forEach(passenger => {
                    const statusBtnClass = passenger.status === 'ACTIVE' ? 'btn-success' : 'btn-danger';
                    const nextStatus = passenger.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';

                    const row = `
              <tr>
               
                <td>${passenger.passengerName}</td>
                <td>${passenger.passengerEmail}</td>
                <td>${passenger.nic}</td>
                <td>${passenger.passengerMobile}</td>
                <td>
                  <button class="btn ${statusBtnClass} toggle-status-btn"
                          data-id="${passenger.nic}"
                          data-next-status="${nextStatus}">
                    ${passenger.status}
                  </button>
                </td>
              </tr>
            `;
                    tbody.append(row);
                });

                attachToggleEvents();
            },
            error: function () {
                new Noty({
                    type: "error",
                    layout: "topRight",
                    text: "Failed to load Passenger: " + xhr.responseText,
                    timeout: 2000
                }).show();
            }
        });
    }

    function attachToggleEvents() {
        $('.toggle-status-btn').off('click').on('click', function () {
            const passengerId = $(this).data('id');  // Get passenger ID
            const newStatus = $(this).data('next-status');
            const actionText = newStatus === 'ACTIVE' ? 'activate' : 'deactivate';

            const confirmationNoty = new Noty({
                text: `Are you sure you want to <strong>${actionText}</strong> this passenger?`,
                type: 'warning',
                layout: 'center',
                theme: 'semanticui',
                killer: true,
                modal: true,
                buttons: [
                    Noty.button('Yes', 'btn btn-success m-1', function () {
                        $.ajax({
                            url: `http://localhost:8080/api/v1/JourneyPass/passenger/updateStatus?nic=${passengerId}&status=${newStatus}`, // Use passengerId instead of nic
                            method: 'PUT',
                            headers: {
                                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                                "Content-Type": "application/json"
                            },
                            success: function (res) {
                                if (res.code === 200) {
                                    new Noty({
                                        text: `Passenger successfully ${actionText}d.`,
                                        type: 'success',
                                        timeout: 2000
                                    }).show();
                                    loadPassengers();  // Assuming this function reloads the passenger list
                                } else {
                                    new Noty({
                                        text: 'Status update failed.',
                                        type: 'error',
                                        timeout: 2000
                                    }).show();
                                }
                            },
                            error: function () {
                                new Noty({
                                    text: 'Something went wrong while updating status.',
                                    type: 'error',
                                    timeout: 2000
                                }).show();
                            }
                        });
                        confirmationNoty.close();  // Close the confirmation popup after the request is made
                    }),

                    Noty.button('No', 'btn btn-danger m-1', function () {
                        confirmationNoty.close(); // Close the modal if 'No' is clicked
                    })
                ]
            });

            confirmationNoty.show();  // Show the confirmation popup
        });
    }
});