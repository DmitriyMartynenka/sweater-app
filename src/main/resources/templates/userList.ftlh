<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role> ${role}<#sep>, </#sep></#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>