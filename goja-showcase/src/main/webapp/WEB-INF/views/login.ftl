<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="robots" content="noindex">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <title>Goja Framework ShowCase Smaple - Login</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/static/styles/main.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/styles/lightadmin.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.lug.ustc.edu.cn/css?family=Cuprum">
<body>

<div class="loginWrapper">
    <div class="loginLogo"><img src="${ctx}/static/images/loginLogo.png" alt="" /></div>
    <div class="loginPanel">
        <div class="head"><h5 class="iUser">Login</h5></div>
        <form action="${ctx}/login" id="valid" class="mainForm">
            <fieldset>
                <div class="loginRow noborder">
                    <label for="req1">Username:</label>
                    <div class="loginInput"><input type="text" name="login" value="Login" class="validate[required]" id="req1" /></div>
                </div>

                <div class="loginRow">
                    <label for="req2">Password:</label>
                    <div class="loginInput"><input type="password" name="password" value="Password" class="validate[required]" id="req2" /></div>
                </div>

                <div class="loginRow">
                    <div class="rememberMe"><input type="checkbox" id="check2" name="chbox" /><label for="check2">Remember me</label></div>
                    <div class="submitForm"><input type="submit" value="Log me in" class="redBtn" /></div>
                </div>
            </fieldset>
        </form>
    </div>
</div>

<!-- Footer -->
<div id="footer">
    <div class="wrapper">
        <span>&copy; Copyright 2011. All rights reserved. This admin theme by <a href="http://lightadmin.org" title="">http://lightadmin.org</a></span>
    </div>
</div>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/spinner/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/spinner/ui.spinner.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.ui-1.8.0.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/tables/colResizable.min.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/forms.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/autotab.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.dualListBox.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.select2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.inputlimiter.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/forms/jquery.tagsinput.min.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/other/calendar.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/other/elfinder.min.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/uploader/plupload.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/uploader/plupload.html5.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/uploader/plupload.html4.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/uploader/jquery.plupload.queue.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.progress.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.jgrowl.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.tipsy.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.alerts.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.colorpicker.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/wizards/jquery.form.wizard.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/wizards/jquery.validate.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.breadcrumbs.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.ToTop.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.listnav.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.sourcerer.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.timeentry.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/vendor/plugins/ui/jquery.prettyPhoto.js"></script>
<script type="text/javascript">
    $(".select").select2();

    $(".selectMultiple").select2();

</script>

</body>
</html>