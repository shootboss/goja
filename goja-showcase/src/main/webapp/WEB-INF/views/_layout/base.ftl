<!DOCTYPE html>
<html>
<head>
    <title>Base Layout</title>
<@block name="scripts">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</@block>
</head>
<body>
<@block name="header">
<h1>Base Layout</h1>
</@block>
<div class="base">
<@block name="contents">
    <h2>Contents will be here</h2>
</@block>
</div>
<@block name="footer">
<div>Footer base</div>
</@block>
</body>
</html>