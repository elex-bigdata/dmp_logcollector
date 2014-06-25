#!/bin/sh

logdir=/data/log/yac/$(date -d"-5 mins" +"%Y%m%d")/
hour=$(`date -d"-5 mins" +"%H"`)

limit=4194304 #暂定每小时日志上限40G左右
size=`du -s ${logdir}/*_${hour}.dat | awk '{sum+=$1;}END{print sum}'`

for f in `find ${logdir} -name *.zip`
do
  if [ ${size} -gt ${limit}];then
    echo "Remove file ${f} as over ${limit} k"
  else
    echo "Unzip and remove file ${f}"
    `unzip -o ${f} -d ${logdir}`
    `mv ${f%.*}.dat ${f%.*}_${hour}.dat`
  fi
  `rm -f ${f}`
done