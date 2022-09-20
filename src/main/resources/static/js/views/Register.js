import CreateView from "../createView.js"

export default function Register(props) {
    return `
    <!DOCTYPE html>
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Register</title>
            </head>
            <body>
                <h1 style="text-align: center">Register</h1>
        
                <form class="form" id="register-form">
                
                    <label for="username">Username</label>
                    <input class="container" id="username" name="username" type="text"/>
                    
                    <label for="email">Email</label>
                    <input class="container" id="email" name="email" type="email">
                    
                    <label for="password">Password</label>
                    <input class="container" id="password" name="password" type="password"/>
                    <button class="btn" style="width: 40%" id="register-btn" type="button">Register</button>
                    
                    <div>
                    <br>
                    <a href="http://localhost:8080/login">Already Registered/ Return to login</a>
                    </div>
                    
                </form>
            </body>
        </html>
`;
}

export function RegisterEvent(){
   // $("#register-btn").click(function(){
    const registerButton = document.querySelector("#register-btn")
    registerButton.addEventListener("click", function(event) {
        const usernameField = document.querySelector("#username");
        const emailField = document.querySelector("#email");
        const passwordField = document.querySelector("#password");

        let newUser = {
            username: usernameField.value,
            email: emailField.value,
            password: passwordField.value
        }

        console.log(newUser);

        let request = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(newUser)
        }

        fetch(USER_API_BASE_URL + "/create", request)
            .then(response => {
                console.log(response.status);
                CreateView("/");
            })

    })
}