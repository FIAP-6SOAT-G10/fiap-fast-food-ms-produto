package br.com.fiap.techchallenge.domain.entities.pagamento;

public class PagamentoResponseDTO {

    private Long id;
    private String status;
    private String detalhes;
    private String qrCodeBase64;
    private String qrCode;
    private String externalId;

    public PagamentoResponseDTO(Long id, String status, String detalhes, String qrCodeBase64, String qrCode) {
        this.id = id;
        this.status = status;
        this.detalhes = detalhes;
        this.qrCodeBase64 = qrCodeBase64;
        this.qrCode = qrCode;
    }

    public PagamentoResponseDTO(Long id, String status, String detalhes) {
        this.id = id;
        this.status = status;
        this.detalhes = detalhes;
    }

    public PagamentoResponseDTO(Long id, String status, String detalhes, String externalId) {
        this.id = id;
        this.status = status;
        this.detalhes = detalhes;
        this.externalId = externalId;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getExternalId() {
        return externalId;
    }
}
