#!/bin/sh
read -t 30 -p "请输入提交日志:" commitLog

git add .
git commit -m $commitLog
git push origin master
