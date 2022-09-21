import CreateView from "../createView.js";
import {getHeaders, getUser, isLoggedIn} from "../auth.js";

let posts;
let loggedInUser;
let categories;

export default function PostIndex(props) {
    //refresh the currently logged-in user
    loggedInUser = getUser();

    const postsHTML = generatePostsHTML(props.posts);
    //save this for loading edits later
    posts = props.posts;
    categories = props.category;

    const addPostHTML = generateAddPostsHTML;

    return `
        <header>
            <h1 style="text-align: center">Posts Page</h1>
        </header>
        <main>
            <h3>Lists of Posts</h3>
            <div>
                ${postsHTML}
            </div>
            
            ${addPostHTML}
            
        </main>
    `;
}

function generateAddPostHTML() {
    let addHTML = ``;

    if(isLoggedIn()) {
        return addHTML;
    }

    const categoryHTML = generateCategoryHTML(categories);

    addHTML = `


            <h3>Add a Post</h3>
            <form>
            <div>
                <label for="category">Category</label><br>
                <select name="category" id="category">
                  <option value="politics">Politics</option>
                  <option value="religion">Religion</option>
                  <option value="technology">Technology</option>
                  <option value="current affairs">Current Affairs</option>
                </select> <br>
            
                <label for="title">Title</label><br>
                <input id="title" style="width: 20%" name="title" type="text" placeholder="Enter a title"> <br>
                <div class="invalid-feedback">
                    Title cannot be blank
                </div>
                <div class="valid-feedback">
                    Your title is ok!
                </div>
            </div>
            <div>    
                <label id="label" for="content">Content</label> <br>
                <textarea id="content" name="content" rows="10" cols="50" placeholder="Enter text"></textarea>
                <div class="invalid-feedback">
                    Content cannot be blank
                </div>
                <div class="valid-feedback">
                    Your content is ok!
                </div>
                <br>
                <button data-id="0" id="savePost" name="savePost" class="button btn-primary">Add/Edit Post</button>
            </div>
            
            <h6 class="my-category-group">Categories</h6>
            ${categoryHTML}
            
             <button data-id="0" id="savePost" name="savePost" type="button" class="my-button button btn-primary">Save Post</button>
            </form>`;

    return addHTML;
}

function generateCategoryHTML(categories) {
    let catHTML = ``;
    for(let i = 0; i < categories.length; i++) {
        const category = categories[i];

        catHTML += `
        <div class="form-check">
            <input class="form-check-input category-checkbox" type="checkbox" value="" data-id="${category.id}" id="category_${category.id}">
            <label class="form-check-label" for="flexCheckDefault">
                ${category.name}
            </label>
        </div>`;
    }
    return catHTML;
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
            authorName = post.author.username; //username is the column name for the user table
        }
        postsHTML = `<tr>
        
        <td>${post.title}</td>
        <td>${post.content}</td>
        <td>${categories}</td>
        <td>${authorName}</td>
        `;
        <!--Show these buttons on posts ONLY when user is author, or if user is admin-->
        // if(isAuthor|| isAdmin) {
        //
        if(loggedInUser) {
            if(loggedInUser.role === 'ADMIN' || loggedInUser.username)
            postsHTML += `
            <td><button data-id=${post.id} class="button rounded btn-primary editPost">Edit</button></td>
            <td><button data-id=${post.id} class="button rounded btn-danger deletePost">Delete</button></td>
        `;
        }
        postsHTML +=`
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
    setupValidationHandlers()
    validateFields();

}

function setupValidationHandlers() {
    let input = document.querySelector("#title");
    input.addEventListener("keyup", validateFields);
    input = document.querySelector("#content");
    input.addEventListener("keyup", validateFields);

}

function validateFields() {
    let isValid = true;
    let input = document.querySelector("#title");
    if(input.value.trim().length < 1) {
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        isValid = false;
    } else {
        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
    }
    input = document.querySelector("#content");

    if(input.value.trim().length < 1) {
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        isValid = false;
    } else {
        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
    }

    return isValid
}


function savePostHandler() {
    const saveButton = document.querySelector("#savePost")
    saveButton.addEventListener("click", function (event) {
        const postId = parseInt(this.getAttribute("data-id"));
        savePost(postId);
    });
}

        function savePost(postId) {
            //get the title and content for the new/updated post

            const titleField = document.querySelector("#title");
            const contentField = document.querySelector("#content");
            const categoryField = document.querySelector("#category");

            //HOLD ON: don't allow save if content is not valid
            if(!validateFields()) {
                return;
            }

            //make the new/saved-edit post object
            const incomingPost = {
                title: titleField.value,
                content: contentField.value,
                category: categoryField.value
            }

            //Make the request
            let request = {
                method: "POST",
                headers: getHeaders(),
                body: JSON.stringify(incomingPost)
            }
            //if we are updating a post, change the request and url
            let url = POST_API_BASE_URL;

            if (postId > 0) {
                request.method = "PUT";
                url += `/${postId}`;
            }

            fetch(url, request)
                .then(response => {
                    if (response.status !== 200) {
                        console.log("fetch returned bad status code " + response.status);
                        console.log(response.statusText);
                        return;
                    }
                    CreateView("/posts");
                })
        }
//end of savePostHandler


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

    const categoryField = document.querySelector("#category")
    titleField.value = post.title
    contentField.value = post.content

    categoryField.value = post.category

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
                    headers: getHeaders(),
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









