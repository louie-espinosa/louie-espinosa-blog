import CreateView from "../createView.js";

let posts;

export default function PostIndex(props) {
    const postsHTML = generatePostsHTML(props.posts);
    //save this for loading edits later
    posts = props.posts;

    return `
        <header>
            <h1 style="text-align: center" >Posts Page</h1>
        </header>
        <main>
            <h3>Lists of Posts</h3>
            <div>
                ${postsHTML}
            </div>

            <h3>Add a Post</h3>
            <form>
                <label for="title">Title</label><br>
                <!--want validation here-->
                <input id="title" style="width: 20%" name="title" type="text" placeholder="Enter a title"> <br>
                
                <label for="author">Author</label><br>
                <input id="author" style="width: 20%" name="author" type="text" placeholder="Enter author name"> <br>
                
                <label for="category">Category</label><br>
                <input id="category" style="width: 20%" name="category" type="text" placeholder="Enter a category"> <br>
                
                <label id="label" for="content">Content</label> <br>
                <textarea id="content" name="content" rows="10" cols="50" placeholder="Enter text"></textarea>
                <br>
                <button data-id="0" id="savePost" name="savePost" class="button btn-primary">Add/Edit Post</button>
            </form>
        </main>
    `;
}

function generatePostsHTML(posts) {
    let postsHTML = `
    <table class="table">
    <thead>
    <tr> <!--using boostrap padding on both side-->
        <th class="col">Title</th>
        <th class="col">Content</th>
        <th class="col">Categories</th>
        <th class="col">Author</th>
    </tr>
    </thead>
    <tbody>
`;
    for (let i = 0; i < posts.length; i++) {
        const post = posts[i];

        let categories = "";
        if(post.categories) {
            for (let j = 0; j < post.categories.length; j++) {
                if (post.categories !== "") {
                    categories += ", ";
                }
                categories += post.categories[j].name;
            }
        }
        let authorName = "";
        if(post.author) {
            authorName = post.author.name;
        }
        postsHTML += `<tr>
        <td>${post.title}</td>
        <td>${post.content}</td>
        <td>${categories}</td>
        <td>${authorName}</td>
        <td><button data-id=${post.id} class="button rounded btn-primary editPost">Edit</button></td>
        <td><button data-id=${post.id} class="button rounded btn-danger deletePost">Delete</button></td>
        </tr>`;
    }
    postsHTML += `</tbody></table>`;
    return postsHTML;
}

//My JS for the PostIndex view
export function postSetup() {
    savePostHandler();
    editPostHandlers();
    deletePostHandlers();
}


function savePostHandler() {
    const saveButton = document.querySelector("#savePost")
    saveButton.addEventListener("click", function(event) {
        const titleField = document.querySelector("#title");
        const contentfield = document.querySelector("#content");

        //make the new/saved-edit post object
        let newPost = {
            title: titleField.value,
            content: contentfield.value
        }
        console.log(newPost);
        //Make the request
        let request = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(newPost)
        }

        fetch(POST_API_BASE_URL, request)
            .then(response => {
                console.log(response.status);
                CreateView("/posts");
            })
    });
}//end of savePostHandler

function editPostHandlers() {
    //target all edit button
    const editButtons = document.querySelectorAll(".editPost");
    //add click handler to all edit buttons
    for (let i = 0; i < editButtons.length; i++) {
        editButtons[i].addEventListener("click", function (event) {

            //get the id of the post from the delete button
            const postId = parseInt(this.getAttribute("data-id"));

            loadPostIntoForm(postId);
        });
    }
}
function loadPostIntoForm(postId) {
    //go find the post in the posts data that matches the postId
    const post = findPostById(postId);
    if(!post) {
        console.log("did not find post for id" + postId);
        return;
    }
    //load the post data into the form
    const titleField = document.querySelector("#title");
    const contentField = document.querySelector("#content");
    titleField.value = post.title
    contentField.value = post.content

    const saveButton  = document.querySelector("#savePost");
    saveButton.setAttribute("data-id", postId);

    function findPostById(postId) {
        for (let i = 0; i < posts.length; i++) {
            if(posts[i].id === postId) {
                return posts[i];
            }
        }
        //didnt find it so return something falsy
        return false;
    }
}//end of editPostHandler

function deletePostHandlers() {
    //target all delete buttons
    const deleteButtons = document.querySelectorAll(".deletePost")
    //add click handler to all delete buttons
    for (let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener("click", function (event) {

            //get the post id of the delete button
            const postId = this.getAttribute("data-id")
            deletePost(postId);

            function deletePost(postId) {
                const request = {
                    method: "DELETE",
                    headers: {"Content-Type": "application/json"},
                }
                const url = POST_API_BASE_URL + `/${postId}`;
                fetch(url, request)
                    .then(response => {
                        if(response.status !== 200) {
                            console.log("fetch returned bad status code: " + response.status);
                            console.log(response.statusText);
                        }
                        CreateView("/posts");
                    })
            }
        })
    }
}









