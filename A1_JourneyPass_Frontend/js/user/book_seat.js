$(document).ready(function () {
    let selectedSeats = [];

    // Select/Deselect seats
    $(".box").not(".booked").click(function () {
        $(this).toggleClass("selected");

        const seatNumber = $(this).text();
        if ($(this).hasClass("selected")) {
            selectedSeats.push(seatNumber);
        } else {
            selectedSeats = selectedSeats.filter(seat => seat !== seatNumber);
        }

        $("#selected-seat").text(`Selected Seat(s): ${selectedSeats.join(", ")}`);
    });

    // Handle form submission
    $("#booking-form").submit(function (event) {
        event.preventDefault();

        const passenger = {
            passenger_id: $("#nic").val(),
            name: $("#full-name").val(),
            email: $("#PassengerEmail").val(),
            phone: $("#mobile").val()
        };

        const bookingData = {
            booking_id: "B" + Date.now(), // Or let backend handle ID
            booking_date: new Date().toISOString().split('T')[0],
            seat_count: selectedSeats.length,
            schedule_id: "WP87-2315-JCA", // You can pull this dynamically if needed
            passenger: passenger
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/JourneyPass/booking/book",
            type: "POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            contentType: "application/json",
            data: JSON.stringify(bookingData),
            success: function (response) {
                alert("Booking successful!");
                // Optional: reload page, redirect, or disable selected seats
            },
            error: function (xhr, status, error) {
                console.error(error);
                alert("Booking failed!");
            }
        });
    });
});