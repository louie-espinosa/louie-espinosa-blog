import createView from "../createView.js";

let me;
export default function userScreenHTML() {
me = prop.me

//make the user's original password available somewhere in here
    return `
    <h1>User Info</h1>
    <h3>${props.me.username}</h3>
    <h3>${props.me.email}</h3>
       
    <form>
        <label for="oldpassword">Please enter your current password</label>
        <input type="password" id="oldpassword" name="oldpassword">
        <br>
        <label for="newpassword">New password</label>
        <input type="password" id="newpassword" name="newpassword">
        <br>
        <label for="confirmpassword">Confirm new password</label>
        <input type="password" id="confirmpassword" name="confirmpassword">
        
        <br>
        <button id="toggleShowPassword" name="toggleShowPassword">Show Password?</button>
        <button id="updatePassword" name="updatePassword">Save New Password</button>
    </form>
    `
}
export function prepareUserJS() {
    doTogglePasswordHandler();
    doSavePasswordHandler();
}

function doSavePasswordHandler() {
    const button = document.getElementById("updatePassword")
    button.addEventListener("click", function(event) {
        //grab the three password field values
        const oldPasswordField = document.querySelector("#oldpassword")
        const newPasswordField = document.querySelector("#newpassword")
        const confirmPasswordField = document.querySelector("#confirmpassword")

        const oldPassword = oldPasswordField.value;
        const newPassword = newPasswordField.value;
        const confirmPassword = confirmPasswordField.value

        const request = {
            method: "PUT",
        }
        const url = `${USER_API_BASE_URL}/${me.id}/updatePassword?oldPassword=${oldPassword}&newPassword=${newPassword}`

        fetch(url, request)
            .then(function(response) {
                CreateView("/");
            });
    });
}

function doTogglePasswordHandler() {
    const button = document.querySelector("#toggleShowPassword");
    button.addEventListener("click", function(event) {
        // grab a reference to confirmpassword
        const oldPassword = document.querySelector("#oldpassword");
        const newPassword = document.querySelector("#newpassword");
        const confirmPassword = document.querySelector("#confirmpassword");
        if(confirmPassword.getAttribute("type") === "password") {
            confirmPassword.setAttribute("type", "text");
            oldPassword.setAttribute("type", "text");
            newPassword.setAttribute("type", "text");
        } else {
            confirmPassword.setAttribute("type", "password");
            oldPassword.setAttribute("type", "password");
            newPassword.setAttribute("type", "password");
        }
    });
}