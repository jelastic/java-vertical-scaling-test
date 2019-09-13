#!/bin/bash
grafana_host=$1
grafana_secret=$(kubectl get secret --namespace kubernetes-monitoring monitoring-grafana -o jsonpath='{.data.admin-password}' | base64 --decode ; echo)
grafana_cred="admin:${grafana_secret}"
grafana_datasource="Prometheus"
ds=(1471 10000 5228 7249);
echo -n "Processing dashboard: "
for d in "${ds[@]}"; do
  echo -n "Processing $d: "
  j=$(curl -s -k -u "$grafana_cred" $grafana_host/api/gnet/dashboards/$d | jq .json)
  curl -s -k -u "$grafana_cred" -XPOST -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -d "{\"dashboard\":$j,\"overwrite\":true, \
        \"inputs\":[{\"name\":\"DS_PROMETHEUS\",\"type\":\"datasource\", \
        \"pluginId\":\"Jelastic\",\"value\":\"$grafana_datasource\"}]}" \
    $grafana_host/api/dashboards/import; echo ""
done