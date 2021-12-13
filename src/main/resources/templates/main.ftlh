<#import "parts/common.ftl" as c>

<@c.page>
    <div class="input-group">
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter!""}" placeholder="Search by tag"/>
            <button class="btn btn-primary ml-2" type="submit">Search</button>
        </form>
    </div>
    <#include "parts/messageEdit.ftl"/>
    <#include "parts/messageList.ftl"/>
</@c.page>
