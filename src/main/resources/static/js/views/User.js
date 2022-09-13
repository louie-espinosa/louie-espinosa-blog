
export default function userScreenHTML() {
//make the user's original password available somewhere in here

}

function createPostHTML(user) {
    let html = `
    <tbody>`;

    //add a row to the table for each user post
    for (let i = 0; i < user.posts.length; i++) {
        const post = user.posts[i];

    }
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

function doToggle