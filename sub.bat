@echo off
call git submodule foreach git pull
pause "Done!"