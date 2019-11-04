NODES=$(kubectl get nodes | grep \<none\> | awk '{print $1}')
NODES=(${NODES// / })
LABELS=(opts=delay1-int30 opts=delay30-int60 opts=delay60-int30 opts=delay30-int1)
for i in ${!NODES[*]}; do
  kubectl label nodes ${NODES[$i]} ${LABELS[$i]} --overwrite
done
