<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager url page/>
<div class="row row-cols-4 row-cols-mt-3" id="message-list">
    <#list page.content as page>
        <div class="card m-2">
            <div>
                <#if page.filename??>
                    <img src="/img/${page.filename}" class="card-img-top">
                </#if>
            </div>
            <div class="m-2">
                <span>${page.text}</span><br/>
                <i>#${page.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${page.author.id}">${page.authorName}</a>
                <#if page.author.id==currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${page.author.id}?message=${page.id}">Edit</a>
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>