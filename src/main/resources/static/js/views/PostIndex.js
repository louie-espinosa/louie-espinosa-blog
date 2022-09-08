import CreateView from "../createView.js";

export default function PostIndex(props) {
    return `
        <header>
            <h1>Posts Page</h1>
        </header>
        <main>
            <div>
                ${props.posts.map(post => `<h3>${post.title}</h3>`).join('')}   
            </div>
        </main>
        <h3>List of posts</h3>
        <div>
        ${props.posts.map(post => `<h3>${post.title}</h3>`).join('')}
        </div>
        
        <h3>Add a post</h3>
        <form>
            <label for="title">Title</label><br>
            <input id="title" name="title" type="text" placeholder="Enter title"/>
            <br>
            <label for="content">Content</label> <br>
            <textarea id="content" name="content" rows="10" cols="50" placeholder="Enter text"></textarea>
            <button id="add-post" type="button">Add</button>
        </form>
        
    `;
}
    export function postSetup() {
       addPostHandler();
       editPostHandlers();
        deletePostHandlers();
}


function addPostHandler() {
    const addButton = document.querySelector("#addPost")
    addButton.addEventListener("click", function(event) {
        const titleField = document.querySelector("#title");
        const contentfield = document.querySelector("#content");

        let newPost = {
            title: titleField.value,
            content: contentfield.value
        }
        console.log(newPost);

        let request = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(newPost)
        }

        fetch("http://localhost:8080/api/users", request)
            .then(response => {
                console.log(response.status);
                CreateView("/posts");
            })
    });
}//end of addPostHandler

function editPostHandlers() {
    const editButtons = document.querySelector("#updatePost")

       for (let i = 0; i < editButtons.length; i++) {
           editButtons[i].addEventListener("click", function (event) {
               alert("post has been edited!")
           })
       }


        let updatePost = {
            title: titleField.value,
            content: contentfield.value
        }
        console.log(updatePost);

        let request = {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatePost)
        }

        fetch("http://localhost:8080/api/users", request)
            .then(response => {
                console.log(response.status);
                CreateView("/posts");
            })

}//end of editPostHandler

function deletePostHandlers() {
    const deleteButton = document.querySelector("#deletePost")
    deleteButton.addEventListener("click", function(event) {
        const titleField = document.querySelector("#title");
        const contentfield = document.querySelector("#content");

        let deletePost = {
            title: titleField.value,
            content: contentfield.value
        }
        console.log(deletePost);

        let request = {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(deletePostPost)
        }

        fetch("http://localhost:8080/api/users", request)
            .then(response => {
                console.log(response.status);
                CreateView("/posts");
            })
    });
}

