#!/bin/sh

logdir=/data/upload/$(date -d"-5 mins" +"%Y%m%d")/

limit=20971520 #暂定每天日志上限20G左右
size=`du -s /data/upload/ | awk '{print $1}'`

for f in `find ${logdir} -name *.zip`
do
  if [ ${size} -gt ${limit}];then
    echo "Remove file ${f} as over ${limit} k"
  else
    echo "Unzip and remove file ${f}"
    `unzip -o ${f} -d ${logdir}`
  fi
  `rm -f ${f}`
done