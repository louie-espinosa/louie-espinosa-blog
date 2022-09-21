import Home from "./views/Home.js";
import PostIndex, {postSetup} from "./views/PostIndex.js";
import About from "./views/About.js";
import Error404 from "./views/Error404.js";
import Loading from "./views/Loading.js";
import Login from "./views/Login.js";
import LoginEvent from "./auth.js";
import Register from "./views/Register.js"
import {RegisterEvent} from "./views/Register.js";
import userScreenHTML, {prepareUserJS} from "./views/User.js";
import Logout, {LogoutEvent} from "./views/Logout.js";
import AboutMe from "./views/AboutMe.js";
import FailedLogin, {FailedLoginEvent} from "./views/FailedLogin.js";
/**
 * Returns the route object for a specific route based on the given URI
 * @param URI
 * @returns {*}
 */
export default function router(URI) {
    const routes = {
        '/': {
            returnView: Home,
            state: {},
            uri: '/',
            title: 'Home',
        },
        '/login': {
            returnView: Login,
            state: {},
            uri: '/login',
            title: "Login",
            viewEvent: LoginEvent
        },
        '/logout': {
            returnView: Logout,
            state: {},
            uri: '/logout',
            title: "Logout",
            viewEvent: LogoutEvent
        },
        '/failedlogin': {
            returnView: FailedLogin,
            state: {},
            uri: '/failedlogin',
            title: "Login Failed",
            viewEvent: FailedLoginEvent
        },
        '/register': {
            returnView: Register,
            state: {},
            uri: '/register',
            title: 'Register',
            viewEvent: RegisterEvent
        },
        '/posts': {
            returnView: PostIndex,
            state: {
                posts: '/api/posts'
            },
            uri: '/posts',
            title: 'All Posts',
            viewEvent: postSetup
        },

        '/about': {
            returnView: About,
            state: {},
            uri: '/about',
            title: 'About',
        },
        '/me': {
            returnView: AboutMe,
            state: {},
            uri: '/me',
            title: 'About Me',
        },
        '/error': {
            returnView: Error404,
            state: {},
            uri: location.pathname,
            title: ' ERROR',
        },
        '/loading': {
            returnView: Loading,
            state: {},
            uri: location.pathname,
            title: 'Loading...',
        }
        // '/user': {//I set this up on sept 14
        //     returnView: userScreenHTML,
        //     state: {
        //         users: '/api/user',
        //     },
        //     uri: '/user',
        //     title: 'User',
        //     viewEvent: prepareUserJS
        // },
    };

    return routes[URI];
}

