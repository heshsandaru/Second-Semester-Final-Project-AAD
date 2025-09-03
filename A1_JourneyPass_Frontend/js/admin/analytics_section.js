$(document).ready(function () {
    loadAllBookings();

    function loadAllBookings() {
        $.ajax({
            url: "http://localhost:8080/api/v1/JourneyPass/booking/getAll",
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            success: function (data) {
                $('#booking-tbody').empty(); // clear old data
                data.forEach(function (booking) {
                    $('#booking-tbody').append(`
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>${booking.passengerId}</td>
                            <td>${booking.scheduleId}</td>
                            <td>${booking.seatsNumber}</td>
                            <td>${booking.bookingStatus}</td>
                            <td>${booking.paymentStatus}</td>
                            <td>${booking.bookedAt}</td>
                        </tr>
                    `);
                });
            },
            error: function (err) {
                console.error("Error loading bookings", err);
            }
        });
    }
});


$('#searchButton').click(function () {
    const searchDate = $('#searchAnalytics').val();

    if (!searchDate) {
        Swal.fire('Error', 'Please enter a date in yyyy-mm-dd format.', 'warning');
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/JourneyPass/booking/searchByDate",
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("authToken"),
            "Content-Type": "application/json"
        },
        data: { date: searchDate },
        success: function (data) {
            if (data.length === 0) {
                Swal.fire('No Results', 'No bookings found for this date.', 'info');
                return;
            }

            let htmlContent = `
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>Passenger ID</th>
                                <th>Schedule ID</th>
                                <th>Seat Number</th>
                                <th>Booking Status</th>
                                <th>Payment Status</th>
                                <th>Booked At</th>
                            </tr>
                        </thead>
                        <tbody>
                `;

            data.forEach(function (booking) {
                htmlContent += `
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>${booking.passengerId}</td>
                            <td>${booking.scheduleId}</td>
                            <td>${booking.seatsNumber}</td>
                            <td>${booking.bookingStatus}</td>
                            <td>${booking.paymentStatus}</td>
                            <td>${booking.bookedAt}</td>
                        </tr>
                    `;
            });

            htmlContent += '</tbody></table>';

            Swal.fire({
                title: `Bookings on ${searchDate}`,
                html: htmlContent,
                width: '90%',
                confirmButtonText: 'Close'
            });
        },
        error: function (err) {
            console.error("Search error", err);
            Swal.fire('Error', 'Something went wrong while fetching data.', 'error');
        }
    });
});