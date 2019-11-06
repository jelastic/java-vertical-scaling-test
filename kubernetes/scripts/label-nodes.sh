NODES=$(kubectl get nodes | grep \<none\> | awk '{print $1}')
NODES=(${NODES// / })
LABELS=($1 $2 $3 $4)
for i in ${!NODES[*]}; do
  kubectl label nodes ${NODES[$i]} ${LABELS[$i]} --overwrite
done
