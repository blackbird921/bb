
<!doctype html>
<html>
<head>
    <title>Basic usage</title>
    <link href="/resources/scripts/kendo/source/styles/kendo.common.css" rel="stylesheet"/>
    <link href="/resources/scripts/kendo/source/styles/kendo.default.css" rel="stylesheet"/>
    <script src="/resources/scripts/jquery-1.7.1.js"></script>
    <script src="/resources/scripts/kendo/kendo.all.js"></script>
</head>
<body>
<a href="../index.html">Back</a>
<div id="example" class="k-content">
    <form method="post" style="width:60%">
        <div>
            <input name="files" id="files" type="file" />
            <p>
                <button type="button" class="k-button k-state-disabled"
                        disabled="disabled">Submit</button>
            </p>
        </div>
    </form>
    <script>
        $(document).ready(function() {
            $("#files").kendoUpload();
        });
    </script>
</div>
</body>
</html>

