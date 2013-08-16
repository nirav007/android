<?php
$rand=uniqid();
move_uploaded_file($_FILES['uploadedfile']['tmp_name'], "./upload/".$rand.$_FILES["uploadedfile"]["name"]);
?>