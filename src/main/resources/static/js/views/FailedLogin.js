import createView from "../createView.js";

export default function FailedLogin(props) {
    return `<h1>Failed login</h1>`;
}
export function FailedLoginEvent() {
    window.setTimeout(function () {
        createView("login");
    }, 3000)
}