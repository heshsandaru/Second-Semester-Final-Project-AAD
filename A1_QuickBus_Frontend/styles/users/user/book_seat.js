$(document).ready(function () {
    let selectedSeats = [];

    $(".box").not(".booked").on("click", function () {
        const seatNumber = $(this).text().trim();

        $(this).toggleClass("selected");

        if ($(this).hasClass("selected")) {
            selectedSeats.push(seatNumber);
        } else {
            selectedSeats = selectedSeats.filter(seat => seat !== seatNumber);
        }

        $("#selected-seat").text("Selected Seat(s): " + selectedSeats.join(", "));
    });

    $("#booking-form").on("submit", function (event) {
        event.preventDefault();

        if (selectedSeats.length === 0) {
            new Noty({
                type: 'warning',
                layout: 'topRight',
                text: '⚠️ Please select at least one seat.',
                timeout: 2000,
                progressBar: true
            }).show();
            return;
        }

        const scheduleId = document.getElementById('scheduleIdInput').value.trim();
        const price = parseFloat(document.getElementById('priceInput').value.trim());

        // const scheduleId = $("#scheduleId").text().trim();
        // const priceText = $("#price").text().trim();
        // const price = parseFloat(priceText);
        //
        // console.log(scheduleId)
        // console.log(price)

        if (!scheduleId || isNaN(price)) {
            console.error("Invalid Schedule ID or Price.");
            return;
        }

        const dataToSend = {
            passengerDTO: {
                nic: $("#nic").val(),
                passengerName: $("#full-name").val(),
                passengerMobile: $("#mobile").val(),
                passengerEmail: $("#PassengerEmail").val()
            },
            bookingDTO: {
                scheduleId: scheduleId,
                seatsNumber: selectedSeats.length,
                seatPrice: price,
                bookedSeatsNumber: selectedSeats
            }
        };

        Swal.fire({
            title: 'Processing...',
            text: 'Booking your seat and sending QR code...',
            allowOutsideClick: false,
            allowEscapeKey: false,
            didOpen: () => Swal.showLoading()
        });

        $.ajax({
            url: "http://localhost:8080/api/v1/JourneyPass/booking/saveFullBooking",
            method: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken")
            },
            data: JSON.stringify(dataToSend),
            success: function () {
                Swal.fire({
                    icon: 'success',
                    title: '🎉 Booking Successful!',
                    text: '',
                    timer: 5000,
                    timerProgressBar: true,
                    showConfirmButton: false
                });

                // Update UI
                selectedSeats.forEach(seat => {
                    $(".box").filter(function () {
                        return $(this).text().trim() === seat;
                    }).addClass("booked").removeClass("selected");
                });

                $("#booking-form")[0].reset();
                selectedSeats = [];
                $("#selected-seat").text("Select your seat:");
            },
            error: function (xhr, status, error) {
                Swal.close();
                console.error("❌ Booking failed:", xhr.responseText || error);
                new Noty({
                    type: 'error',
                    layout: 'topRight',
                    text: '❌ Booking failed. Please check your details and try again.',
                    timeout: 3000,
                    progressBar: true
                }).show();
            }
        });
    });
});