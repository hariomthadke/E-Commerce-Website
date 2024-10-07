<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Code Verification</title>
<%@include file="/Components/common_css_js.jsp"%>
<style>
    label {
        font-weight: bold;
    }
    .otp-input {
        width: 50px;
        height: 50px;
        text-align: center;
        font-size: 24px;
        margin: 0 5px;
        border: 1px solid #ced4da;
        border-radius: 5px;
    }
    .otp-container {
        display: flex;
        justify-content: center;
        margin-bottom: 15px;
    }
    .otp-container input:focus {
        border-color: #80bdff;
        outline: none;
        box-shadow: 0 0 5px rgba(128, 189, 255, 0.5);
    }
</style>
</head>
<body>
    <!--navbar -->
    <%@include file="/Components/navbar.jsp"%>

    <div class="container-fluid ">
        <div class="row mt-5">
            <div class="col-md-4 offset-md-4">
                <div class="card">
                    <div class="card-body px-5">

                        <div class="container text-center">
                            <img src="Images/forgot-password.png" style="max-width: 100px;"
                                class="img-fluid">
                        </div>
                        <h3 class="text-center mt-3">OTP Verification</h3>
                        <%@include file="/Components/alert_message.jsp"%>

                        <form action="verifyCode" method="post" id="otpForm">
                            <div class="mb-3">
                                <div class="otp-container">
                                    <input type="text" maxlength="1" class="otp-input" id="otp1" name="otp1" required autofocus>
                                    <input type="text" maxlength="1" class="otp-input" id="otp2" name="otp2" required>
                                    <input type="text" maxlength="1" class="otp-input" id="otp3" name="otp3" required>
                                    <input type="text" maxlength="1" class="otp-input" id="otp4" name="otp4" required>
                                    <input type="text" maxlength="1" class="otp-input" id="otp5" name="otp5" required>
                                </div>
                                <!-- Hidden input to store the concatenated OTP -->
                                <input type="hidden" name="code" id="code">
                            </div>
                            <div class="container text-center">
                                <button type="submit" class="btn btn-outline-primary me-3">Verify</button>
                            </div>
                        </form>
                         <div class="container text-center">
                            <div class="text-sm text-slate-500 mt-4 d-flex justify-content-center">
                                Didn't receive code? 
                                <a id="resendLink" class="font-medium text-indigo-500 hover:text-indigo-600 text-decoration-none disabled" href="resendOTP">Resend</a>
                                <span id="countdown"> (60s)</span>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript for OTP functionality -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const otpInputs = document.querySelectorAll('.otp-input');

            otpInputs.forEach((input, index) => {
                input.addEventListener('input', () => {
                    const value = input.value;
                    if (/[^0-9]/.test(value)) {
                        input.value = '';
                        return;
                    }
                    if (value.length === 1 && index < otpInputs.length - 1) {
                        otpInputs[index + 1].focus();
                    }
                });

                input.addEventListener('keydown', (e) => {
                    if (e.key === 'Backspace' && input.value === '' && index > 0) {
                        otpInputs[index - 1].focus();
                    }
                });
            });

            // Before form submission, concatenate the OTP values
            const otpForm = document.getElementById('otpForm');
            otpForm.addEventListener('submit', function (e) {
                let otp = '';
                otpInputs.forEach(input => {
                    otp += input.value;
                });
                document.getElementById('code').value = otp;

                // Optional: Validate OTP length
                if (otp.length < 5) {
                    e.preventDefault();
                    alert('Please enter a 5-digit OTP.');
                }
            });
        });
        
        document.addEventListener("DOMContentLoaded", function () {
            var resendLink = document.getElementById("resendLink");
            var countdown = document.getElementById("countdown");
            var seconds = 60;

            function updateCountdown() {
                if (seconds > 0) {
                    seconds--;
                    countdown.textContent = `  (wait)`;
                } else {
                    resendLink.classList.remove("disabled");
                    resendLink.style.pointerEvents = "auto";
                    countdown.style.display = "none";
                }
            }

            resendLink.classList.add("disabled");
            resendLink.style.pointerEvents = "none";
            setInterval(updateCountdown, 1000);
        });
    </script>
</body>
</html>
