<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Profile">

    <h1 class="row">User Profile</h1>
    <hr>
    <div class="row text-large">

        <!-- User's info -->
        <div class="col-lg-4">
            <div class="row">
                <div class="col-lg-8">
                    <span class="text-muted">Username: </span>${user_now.username}<br>
                    <span class="text-muted">Password: </span>${user_now.password}<br>
                    <span class="text-muted">Name: </span>${user_now.name}<br>
                    <span class="text-muted">Surname: </span>${user_now.surname}<br>
                    <span class="text-muted">Role: </span>
                    <#list user_now.getAuthorities() as role>
                        ${role}
                    </#list>
                </div>
                <div class="col-lg-4">
                    <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#editProfileModal"> Edit </button>
                </div>
            </div>
        </div>

        <!-- Edit modal -->
        <@m.modal "editProfileModal" "profile" "Update" "Edit information">
            <form method="post" action="/profile" id="profile">
                <div class="form-group">
                    <label for="name" class="col-form-label">Username:</label>
                    <input type="text" class="form-control" name="username">
                </div>
                <div class="form-group">
                    <label for="password" class="col-form-label">Password:</label>
                    <input class="form-control" name="password" type="password">
                </div>
                <div class="form-group">
                    <label for="name" class="col-form-label">Name:</label>
                    <input class="form-control" name="name" type="text">
                </div>
                <div class="form-group">
                    <label for="surname" class="col-form-label">Surname:</label>
                    <input class="form-control" name="surname" type="text">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </form>
        </@m.modal>

        <div class="col-lg-8 text-left">
            <div class="row">

                <!-- Projects user created -->
                <div class="col-lg-6">
                    <div class="list-group list-group-flush shadow">
                       <span class="list-group-item active">Suggested projects</span>
                        <#list user_now.getCreatedProjects() as project>
                            <a href="/project/${project.id}" class="list-group-item list-group-item-action">
                                ${project.name}
                            </a>
                        </#list>
                    </div>
                </div>

                <!-- Projects user is a part of a team -->
                <#if user_now.isCurator() || user_now.isUser()>
                    <div class="col-lg-6">
                        <div class="list-group list-group-flush shadow">
                            <#if !user_now.isCurator()>
                                <li class="list-group-item list-group-item-action active">Participated in projects</li>
                            <#else>
                                <li class="list-group-item list-group-item-action active">Curator of projects</li>
                            </#if>
                            <#list projects as project>
                                <a href="/project/${project.id}" class="list-group-item list-group-item-action">
                                    ${project.name}
                                </a>
                            </#list>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</@p.page>